'''from openai import OpenAI

gpt = OpenAI(
    api_key="YOUR_API_KEY"
) 

prompt = input("ASK AWAY: ")

response = gpt.completions.create(model="gpt-3.5",prompt=prompt)

print(response)'''

from openai import OpenAI,OpenAIError

# Set your API key here

api_key_val = "YOUR_API_KEY"

def get_completion(prompt, api_key=api_key_val):
    try:
        if api_key:
            client = OpenAI(api_key=api_key)
        else:
            client = OpenAI()
        
        completion = client.chat.completions.create(
            model="gpt-3.5-turbo",
            messages=[
              {"role": "system", "content": "You are an SQL Query Generation Assistant. Your role is to craft precise and optimized SQL queries based on user requirements. You excel in analyzing requirements, generating SQL queries, optimizing performance, and providing error handling and documentation.You finallay return to the user an optimized SQL query for their scenario without anything else dont even include code blocks or uneccessay characters"},
              {"role": "user", "content": prompt},
            ]

        )
        
        return str(list(completion.choices[0].message)[0][1])
    except OpenAIError as e:
        return f"Error: {e}"

# Example usage:
'''prompt = "Retrieve the top 5 customers who have made the highest total purchases"


result = get_completion(prompt)
print(result)
'''