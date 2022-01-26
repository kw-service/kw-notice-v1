import json
import pymysql
from db_connection import connect_to_rds

# Return on request with invalid type
def invalid_type_request(type):
    return {
        'status_code' : 404,
        'message' : 'Invalid type request : ' + type
    }

# Return on request with invalid id
def invalid_id_request(table, id):
    return {
        'status_code' : 404,
        'message' : 'There is no ID = ' + str(id) + ' in ' + table
    }

    
def lambda_handler(event, context):
    # Check type with request resouce path    
    valid_resource_path = ['kw-home', 'sw-central']
    type = event['context']['resource-path'].split('/')[1]
    
    # Invalid type
    if type not in valid_resource_path:
        return invalid_type_request(type)
    
    # Get ID on path parameter
    path = event['params']['path']
    id =  None if len(path) == 0 else int(path['id'])
    
    conn, cursor = connect_to_rds()
    
    # Set table name
    table = ''
    if type == 'kw-home': table = 'KW_HOME'
    else: table = 'SW_CENTRAL'
    
    # MySQL query
    query = 'SELECT * from ' + table
    if id is not None:
        query += ' WHERE id = ' + str(id)
        
    cursor.execute(query)
    result = cursor.fetchall()
    
    # Empty result of notices
    if len(result) == 0: return invalid_id_request(table, id)
    
    notice_list = []
    
    # JSON serialization
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
        
    result ={
        'status_code' : 200,
        'data' : notice_list
    }
    
    return result