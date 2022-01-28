import json
import pymysql
from db_connection import connect_to_rds

def invalid_type_request(type):
    return {
        'status_code' : 404,
        'message' : 'Invalid type request : ' + type
    }


def invalid_id_request(table, id):
    return {
        'status_code' : 404,
        'message' : 'There is no ID = ' + str(id) + ' in ' + table
    }

    
def lambda_handler(event, context):
        
    valid_resource_path = ['kw-home', 'sw-central']
    type = event['context']['resource-path'].split('/')[1]
    
    if type not in valid_resource_path:
        return invalid_type_request(type)
    
    path = event['params']['path']
    id =  None if len(path) == 0 else int(path['id'])
    
    conn, cursor = connect_to_rds()
    
    table = ''
    if type == 'kw-home': table = 'KW_HOME'
    else: table = 'SW_CENTRAL'
    
    query = 'SELECT * from ' + table
    if id is not None:
        query += ' WHERE id = ' + str(id)
        
    cursor.execute(query)
    result = cursor.fetchall()
    
    if len(result) == 0: return invalid_id_request(table, id)
    
    notice_list = []
    
    if table == 'KW_HOME':
        for row in result:
            notice_list.append({
                'id' : row[0],
                'title' : row[1],
                'tag' : row[2],
                'posted_date' : str(row[3]),
                'modified_date' : str(row[4]),
                'department' : row[5],
                'url' : row[6],
                'type' : row[7],
                'crawled_time' : str(row[8])
            })
            
        notice_list.sort(key = lambda x: (x['modified_date'], x['crawled_time']), reverse=True)
        
    elif table == 'SW_CENTRAL':
        for row in result:
            notice_list.append({
                'id' : row[0],
                'title' : row[1],
                'posted_date' : str(row[2]),
                'url' : row[3],
                'type' : row[4],
                'crawled_time' : str(row[5])
            })
        
        notice_list.sort(key = lambda x: (x['posted_date'], x['crawled_time']), reverse=True)
    
    return notice_list