from flask import Flask, jsonify, request
from databaseMYSQLver import connection
import json

app = Flask(__name__)

# Global variable to store the database connection
db_connection = None

def get_db_connection():
    global db_connection
    if db_connection is None:
        creds = open('credentials.json', 'r')
        credentials = json.load(creds)

        # Attempt to establish a new connection if it doesn't exist
        try:
            db_connection = connection(credentials['host'], credentials['user'], credentials['password,'])
        except Exception as e:
            print("Can't Connect Without Credentials!", e)
    return db_connection

def to_json(header, data):
    json_data = {}

    # Add header tuple to JSON data
    header_key = header[0]
    json_data[header_key] = []

    for tup in data:
        value = tup[0]
        json_data[header_key].append(value)

    print("returned : ", json_data)
    return json_data


@app.route('/data', methods=['GET', 'POST'])
def handle_data():
    global db_connection

    if request.method == 'POST':
        req_data = request.get_json()
        print("Received data:", req_data)
      
        creds = 'credentials.json'
        query = "queries.json"
        
        if "host" in req_data.keys():
            with open(creds, 'w') as json_file:
                json.dump(req_data, json_file, indent=4)
        if "query" in req_data.keys():
            with open(query, 'w') as json_file:
                json.dump(req_data, json_file, indent=4)
        
        
        return jsonify({"message": "Data received successfully"})
    
    elif request.method == 'GET':
        print("IN GET")
        # Get the database connection
        con = get_db_connection()
        if con is None:
            return jsonify({"error": "Unable to establish database connection"})

        creds = open('credentials.json','r')
        credentials = json.load(creds)
        
        queryFile = open('queries.json','r')
        queries = json.load(queryFile)
        
        cursor = con.cursor()
        
        if "query" in queries:
            try:
                cursor.execute(queries["query"])
                resultSet = cursor.fetchall()
            except Exception as e:
                print("Error executing query:", e)
                resultSet = []
        try:    
            header = [desc[0] for desc in cursor.description]    
            resultSet = to_json(header,resultSet)
        except Exception as e:
            print("Error converting to JSON:", e)
            resultSet = {"status": "executed"}
        
        return jsonify(resultSet)

if __name__ == '__main__':
    app.run(debug=True)
