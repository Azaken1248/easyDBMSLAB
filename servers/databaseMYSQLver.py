import mysql.connector as mscon

def connection(hostvalue,username,passkey):
    con = mscon.connect(host = hostvalue, user = username, password = passkey)
    print("\nconnection successful!")

    return con 
                        
def queryMode(cursor,query):
    rawdata = None
    try:
        cursor.execute(query)
    except:
        pass
    rawdata = cursor.fetchall()
    return rawdata