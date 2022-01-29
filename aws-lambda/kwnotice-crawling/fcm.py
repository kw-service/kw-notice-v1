from pyfcm import FCMNotification
import env

SERVER_KEY = env.FCM_SERVER_KEY
DEVICE_TOKEN = 'dnQDgh-EQcSkZnC5gyhTz6:APA91bFI6qzsMQ-cOL49vspaJQLbC2VxO5C2WzscSOSEtZBCRU98Y9IiO4U1inM3yrG4x_iVsHkNh5RSKe0-T2ow9tQaGlVXcvMTiBUKdBgz_9xjYnPdsEs01kSRZP3ZyVxSixpSLgKj'
KW_HOME_TOPIC = 'kw-home'
SW_CENTRAL_TOPIC = 'sw-central'

def pushNotification(title, body, url):
    fcm = FCMNotification(SERVER_KEY)

    message = {
        "title" : title,
        "body" : body,
    }

    result = fcm.notify_topic_subscribers(
        topic_name = KW_HOME_TOPIC,
        data_message = message
    )

    print(result)

pushNotification("ㅎㅇ","ㅇㅇ","https://yjyoon-dev.github")