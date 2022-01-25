import os
import pymysql
import env

def connect_to_rds():
    host = env.DB_HOST
    port = env.DB_PORT
    user = env.DB_USER
    database = env.DB_NAME
    password = env.DB_PASSWORD
    
    conn = pymysql.connect(host=host, user=user, passwd=password, db=database, port=port, use_unicode=True, charset='utf8')
    cursor = conn.cursor()
    
    return conn, cursor