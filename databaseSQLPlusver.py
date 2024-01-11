import subprocess
import getpass as gp

def connection():
    hostvalue = input("Enter Your Host: ")
    username = input("Enter Your Username: ")
    passkey = gp.getpass("Enter Your Password: ")
    
    connectionStr = f'{username}/{passkey}@localhost:3300'