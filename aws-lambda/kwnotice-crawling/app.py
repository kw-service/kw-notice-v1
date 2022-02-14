import pymysql

from kw_home import crawl_kw_home
from sw_central import crawl_sw_central
from db_connection import connect_to_rds

def handler(event, context):
    # DB connection
    conn, cursor = connect_to_rds()

    # Crawling
    crawl_kw_home(conn, cursor)
    crawl_sw_central(conn, cursor)

    conn.close()