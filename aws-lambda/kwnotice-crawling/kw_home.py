from datetime import datetime
import pymysql
import requests
from bs4 import BeautifulSoup
from fcm import pushNotification

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36'
}

def crawl_kw_home(conn, cursor):
    BASE_URL = 'https://www.kw.ac.kr/ko/life/notice.jsp?srCategoryId=&mode=list&searchKey=1&searchVal='

    r = requests.get(BASE_URL, headers=headers)
    soup = BeautifulSoup(r.content, 'html.parser')

    board_list = soup.find("div", {"class":"board-list-box"})
    notice_list = board_list.findAll("li")

    query = "SELECT url FROM KW_HOME"
    cursor.execute(query)
    crawled_url_list = set(row[0] for row in cursor.fetchall())

    for notice in notice_list:
        title = notice.find("div", {"class":"board-text"}).find("a").text.replace('신규게시글','').replace('Attachment','').replace("'",'"').strip()
        tag = notice.find("div", {"class":"board-text"}).find("a").find("strong", {"class":"category"}).text.replace('[','').replace(']','').strip()
        info = notice.find("div", {"class":"board-text"}).find("p", {"class":"info"}).text.split(' | ')
        url = notice.find("div", {"class":"board-text"}).find("a").attrs['href'].strip()

        posted_date = info[1].split()[1]
        modified_date = info[2].split()[1]
        department = info[3]
        type = 'KW_HOME'
        crawled_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        query = ''
        if url in crawled_url_list:
            cursor.execute("SELECT modified_date FROM KW_HOME WHERE url = '{}'".format(url))
            old_modified_date = cursor.fetchone()[0]
            if str(modified_date) != str(old_modified_date):
                query = "UPDATE KW_HOME SET modified_date = '{}', crawled_time = '{}' WHERE url = '{}'".format(modified_date, crawled_time, url)
                
                pushNotification('광운대학교에 수정된 공지사항이 있어요!', title, url, 'kw-home-edit')
        else:
            query = "INSERT INTO KW_HOME(title, tag, posted_date, modified_date, department, url, type, crawled_time) VALUES ('{}','{}','{}','{}','{}','{}','{}','{}')".format(title, tag, posted_date, modified_date, department, url, type, crawled_time)
            
            pushNotification('광운대학교에 새 공지사항이 올라왔어요!', title, url, 'kw-home-new')
        
        if query != '': cursor.execute(query)
    
    conn.commit()