from openai import OpenAI

gpt = OpenAI(
    api_key="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"
) 

prompt = input("ASK AWAY: ")

response = gpt.completions.create(model="gpt-3.5",prompt=prompt)

print(response)