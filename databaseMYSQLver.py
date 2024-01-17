import mysql.connector as mscon
import getpass as gp

def connection():
    hostvalue = input("Enter Your Host: ")
    username = input("Enter Your Username: ")
    passkey = gp.getpass("Enter Your Password: ")
    
    con = mscon.connect(host = hostvalue, user = username, password = passkey)
    
    print("\nconnection successful!")

    return con

def databaseEditor(cursor):
    while True:
        print("\nHere Is The List Of Data Bases:-")
    
        cursor.execute("show databases")
        databases = cursor.fetchall()
        for database in databases:
            print(database[0])
    
        try:
            database = input("\nType The Database Name To Add It And To Delete it do D! <Name>(ctrl+C To Exit): ")
        except KeyboardInterrupt:
            print("\n")
            return None
        
        if("D!" in database):
            tempList = list(database.split())
            try:
                cursor.execute("drop database %s"%tempList[1])
            except:
                print("\nWrong Format!\n")
        else:
            try:
                cursor.execute("create database %s"%database)
            except:
                print("\nWrong Format!\n")
    
def tableEditor(cursor):
    while True:
        print("\nHere Is The List Of Data Bases:-")

        dbList = []
        
        cursor.execute("show databases")
        databases = cursor.fetchall()
        for database in databases:
            dbList.append(database[0])
            print(database[0])
        
        try:
            selectedDatabase = input("Which Database Would You Like To Work With(ctrl+C To Exit): ")
        except KeyboardInterrupt:
            print("\n")
            return None
        
        if(selectedDatabase not in dbList):
            print("\nThis Database Is Not In List!\n")
            continue
        else:
            while True:
                cursor.execute("use {}".format(selectedDatabase))
                print("\nHere Is The List Of Tables in %s:-"%selectedDatabase)
            
                cursor.execute("show tables")
                tables = cursor.fetchall()
                for table in tables:
                    print(table[0])
                
                try:
                    print("\nNote: Please Type The Atrribute In Format <Name> <datatype> <properties>\n")
                    table = input("Type The <Table Name>-<attributes seperated by ','> To Add It And To Delete it do D! <Name>(ctrl+C To Exit): ")
                except KeyboardInterrupt:
                    print("\n")
                    return None
        
                if("D!" in table):
                    tempList = list(table.split())
                    try:
                        cursor.execute("drop table %s"%tempList[1])
                    except:
                        print("\nTable Not Dropped!!\n")
                else:
                    table_data = table.split('-', 1)
                    table_name = table_data[0].strip()
                    table_attributes = table_data[1].strip().replace(',', ', ')

                    create_table_query = "CREATE TABLE IF NOT EXISTS `{}` ({})".format(table_name, table_attributes)
                    #   print("Log : ",create_table_query)
                    try:
                        cursor.execute(create_table_query)
                        print("Table {} created successfully.".format(table_name))
                    except Exception as e:
                        print("Table creation failed: ",e)                
                
            
        
        
        
def valueEditor(cursor):
    while True:
        print("\nHere Is The List Of Data Bases:-")

        dbList = []
        
        cursor.execute("show databases")
        databases = cursor.fetchall()
        for database in databases:
            dbList.append(database[0])
            print(database[0])
        
        try:
            selectedDatabase = input("Which Database Would You Like To Work With(ctrl+C To Exit): ")
        except KeyboardInterrupt:
            print("\n")
            return None
        
        if(selectedDatabase not in dbList):
            print("\nThis Database Is Not In List!\n")
            continue
        else:
            while True:
                cursor.execute("use {}".format(selectedDatabase))
                print("\nHere Is The List Of Tables in %s:-"%selectedDatabase)

                tbList = []
                
                cursor.execute("show tables")
                tables = cursor.fetchall()
                for table in tables:
                    tbList.append(table[0])
                    print(table[0])
                    
                try:
                    selectedTable = input("Which Table Would You Like To Work With(ctrl+C To Exit): ")
                except KeyboardInterrupt:
                    print("\n")
                    return None
                
                if(selectedTable not in tbList):
                    print("\nThis Table Is Not In List!\n")
                    continue
                else:
                    while True:
                        paramList = []
                    
                        print("\nHere is the table Description:-\n")
                        print("Field\tType\tNull\tKey\tDefault\tExtra")
                        cursor.execute("desc {}".format(selectedTable))
                        desc = cursor.fetchall()
                        for descriptor in desc:
                            paramList.append(descriptor[0])
                            for field in descriptor:
                                print(field,end="\t")
                            print("\n")
                        print("\nType values Seperated By Comma To Insert Into Table(any skipped values MUST BE replaced by NULL)")
                        print("\nTo Delete Data type D! <attribute>=<value> ")

                        try:
                            action = input("Perform Action(ctrl+C to exit): ")
                        except KeyboardInterrupt:
                            print("\n")
                            return None
                    
                        if("D!" in action):
                            tempList = list(action.split())
                            attribute,value = tempList[1].split("=")
                            try:
                                cursor.execute("delete from {} where {} = {}".format(selectedTable,attribute,value))
                            except:
                                print("\nDeletion Failed!!\n")
                                continue
                            print("\nDeleted Successfully!!\n")                        
                        else:
                            attributesStr = ", ".join(paramList)
                            placeholders = ", ".join(["%s" for _ in paramList])
        
                            try:
                                cursor.execute("INSERT INTO {} ({}) VALUES ({})".format(selectedTable, attributesStr, placeholders), action.split(','))
                                print("\nInserted Successfully!!\n")
                            except Exception as e:
                                print("\nInsertion Failed: ", e, "\n")
                                continue
                        

try:
        con = connection()
except:
    print("connection failed!")
    print("\n\nAttempting to reconnect...\n\n")
    try:
        print("Please re enter details(ctrl+c to exit):-")
        con = connection()
    except KeyboardInterrupt:
         exit()
    

def queryMode(cursor):
     
    while True:
        try:
            query = input("\nEnter Your Query(ctrl+C To Exit): \n")
        except KeyboardInterrupt:
            print("\n")
            return None            
        try:
            cursor.execute(query)
        except:
            print("\nQuery Execution Failed!!\n")
            continue

        print("\n")
        data = cursor.fetchall()
     
        if(data):
            for rawdata in data:
                print(rawdata)
        else:
            print("\nQuery Executed Sucessfully!\n")
        
        print("\n")

def InteractiveMode(cursor):
     
    try:
        taskChoice = int(input("\nWhat Would You Like To Do:-\n(1) Create\Delete Databases\n(2) Create\Delete Tables In A Database\n(3) Insert\Delete Data into table\nEnter Your Choice(ctrl+C To Exit): "))
    except KeyboardInterrupt:
        print("\n")
        return None
    
    if(taskChoice == 1):
        databaseEditor(cursor)
    elif(taskChoice == 2):
        tableEditor(cursor)
    elif(taskChoice == 3):
        valueEditor(cursor)
    else:
        print("INVALID Choice!!")
      

while True:
    
    

    cursor = con.cursor()

    try:
        modechoice = int(input("\nChoose Mode:-\n(1) Query Mode\n(2) Interactive Mode\nEnter Your Choice: "))
    except KeyboardInterrupt:
        commit_status = input("\nCommit Changes(y/n): ")
        if(commit_status in 'yY'):
            cursor.execute("commit")
            print("\nChanges Saved.\n")
        print("\nForce Exit...Done!\n")
        exit()
    except ValueError:
        print("\nWrong Format!!!\n")
        print("\nselecting defualt mode choice...\n")
        modechoice = 1

    if(modechoice == 1):
         queryMode(cursor)
    elif(modechoice == 2):
        InteractiveMode(cursor)
    else:
         print("\nINVALID Choice!!\n")