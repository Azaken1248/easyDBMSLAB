import cx_Oracle as scon
import getpass as gp

def connection():
    hostvalue = input("Enter Your Host: ")
    port = input("Enter Port: ")
    service_name = input("Enter Service Name(run SELECT sys_context('userenv', 'service_name') FROM dual; in your sql): ")
    
    username = input("Enter Your Username: ")
    passkey = gp.getpass("Enter Your Password: ")
    
    connection_string = '{}:{}/{}'.format(hostvalue,port,service_name)
    
    con = scon.connect(username, passkey, connection_string)
    
    print("\nconnection successful!\n")
    
    return con

'''try:
        con = connection()
except:
    print("connection failed!")
    print("\n\nAttempting to reconnect...\n\n")
    try:
        print("Please re enter details(ctrl+c to exit):-")
        con = connection()
    except KeyboardInterrupt:
         exit()'''

con = connection()