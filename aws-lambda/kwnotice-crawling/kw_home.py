from datetime import datetime
import pymysql
from selenium import webdriver
from selenium.webdriver.common.by import By

def get_driver():
    options = webdriver.ChromeOptions()
    options.binary_location = '/opt/chrome/chrome'
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument("--disable-gpu")
    options.add_argument("--window-size=1280x1696")
    options.add_argument("--single-process")
    options.add_argument("--disable-dev-shm-usage")
    options.add_argument("--disable-dev-tools")
    options.add_argument("--no-zygote")
    options.add_argument('user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36')
    
    driver = webdriver.Chrome("/opt/chromedriver", options=options)
    return driver

def crawl_kw_home(conn, cursor):
    BASE_URL = 'https://www.kw.ac.kr/ko/life/notice.jsp?srCategoryId=&mode=list&searchKey=1&searchVal='

    driver = get_driver()
    driver.get(BASE_URL)

    notice_list = driver.find_elements(By.CSS_SELECTOR, '#jwxe_main_content > div > div > div.list-box > div > ul > li.top-notice')

    query = "SELECT url FROM KW_HOME"
    cursor.execute(query)
    crawled_url_list = set(row[0] for row in cursor.fetchall())

    for notice in notice_list:
        title = notice.find_element(By.CSS_SELECTOR, 'div.board-text > a').text.replace('신규게시글','').replace('Attachment','').replace("'",'"').strip()
        tag = notice.find_element(By.CSS_SELECTOR, 'div.board-text > a > strong.category').text.replace('[','').replace(']','')
        info = notice.find_element(By.CSS_SELECTOR, 'div.board-text > p.info').text.split(' | ')
        posted_date = info[1].split()[1]
        modified_date = info[2].split()[1]
        department = info[3]
        url = notice.find_element(By.CSS_SELECTOR, 'div.board-text > a').get_attribute('href')
        type = 'KW_HOME'
        crawled_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        
        query = ''
        if url in crawled_url_list:
            query = "UPDATE KW_HOME SET modified_date = '{}', crawled_time = '{}' WHERE url = '{}'".format(modified_date, crawled_time, url)
        else:
            query = "INSERT INTO KW_HOME(title, tag, posted_date, modified_date, department, url, type, crawled_time) VALUES ('{}','{}','{}','{}','{}','{}','{}','{}')".format(title, tag, posted_date, modified_date, department, url, type, crawled_time)
        cursor.execute(query)
    
    driver.close()
    conn.commit()
