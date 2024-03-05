from openai import OpenAI

gpt = OpenAI(
    api_key="INSERT API KEY"
) 

prompt = input("ASK AWAY: ")

response = gpt.completions.create(model="gpt-3.5",prompt=prompt)

print(response)