from flask import Flask, jsonify, request
from databaseMYSQLver import connection,queryMode
import json


def toJson(header, data):
    json_data = {}

    # Add header tuple to JSON data
    header_key = header[0]
    json_data[header_key] = []

    for tup in data:
        value = tup[0]
        json_data[header_key].append(value)
        
    print("returned : ",json_data)
    return json_data


app = Flask(__name__)



@app.route('/data', methods=['GET', 'POST'])
def handle_data():
    if request.method == 'POST':
        req_data = request.get_json()
        print("Received data:", req_data)
      
        filename = 'data.json'
        
        with open(filename, 'w') as json_file:
            json.dump(req_data, json_file, indent=4)
        
        
        return jsonify({"message": "Data received successfully"})
    
    elif request.method == 'GET':
        file = open('data.json','r')
        data = json.load(file)
        
        con = None
        
        try:
            con = connection(data['host'],data['user'],data['passwprd'])
        except KeyError:
            print("Can't Connect Without Credentials!")
        
        cursor = con.cursor()
        
        return jsonify(data)

if __name__ == '__main__':
    app.run(debug=True)
