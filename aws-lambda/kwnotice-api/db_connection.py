import os
import pymysql

def connect_to_rds():
    host = os.environ['DB_HOST']
    port = int(os.environ['DB_PORT'])
    user = os.environ['DB_USER']
    database = os.environ['DB_NAME']
    password = os.environ['DB_PASSWORD']
    
    conn = pymysql.connect(host=host, user=user, passwd=password, db=database, port=port, use_unicode=True, charset='utf8')
    cursor = conn.cursor()
    
    return conn, cursor