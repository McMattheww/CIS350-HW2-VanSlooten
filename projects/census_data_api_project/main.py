#Matthew VanSlooten CIS 123 final project
#program asks a question comparing state industries 10/11 times on average
#program asks a question comparing state poverty levels 1/11 times on average

import requests
import json
import random


def jprint(obj):
    # create a formatted string of the Python JSON object
    text = json.dumps(obj, sort_keys=True, indent=4)
    print(text)
#function dumps, then prints json object as string


def get_census_data_state(obj):
    # function accepts two digit state code string as argument,
    # returns a float which is the percentage rate of poverty in that state.
    response = requests.get(f"https://api.census.gov/data/timeseries/poverty/saipe?get=SAEPOVRTALL_PT&for=state:{obj}&time=2018")
    response_data = response.json()
    poverty_data_list = response_data[1]
    poverty_data = float(poverty_data_list[0])
    return poverty_data

def get_industry_list_state(obj):
    #Takes a two digit state code string as an argument, returns a dictionary with the industries in that state as keys,
    #and the number of employees working in that industry as the value as a string
    var1 = requests.get(f"https://api.census.gov/data/2017/ecnbasic?get=NAICS2017,NAICS2017_LABEL,EMP,NAME&for=state:{obj}")
    response_data = var1.json()
    del response_data[0]
    final_dictionary = {}
    for industry_data in response_data:
        naics_code = industry_data[0]
        if len(naics_code) > 4:
            final_dictionary[industry_data[1]] = industry_data[2]
    return final_dictionary

##########################
###initialize variables###
#todo insert   if __name__ == "__main__":
#add to main code body excluding function definitions and import statements


# current score
points = 0

# Initialize state_info dictionary. Dictionary contains each state's name as a key,
# and that state's ID code used for API calls as the value
state_info = {}
response_state_names = requests.get(f"https://api.census.gov/data/timeseries/poverty/saipe?get=NAME&for=state:*&time=2018")
state_names = response_state_names.json()
for state in state_names:
    state_info[state[0]] = [state[2]]
del state_info['NAME']


# while loop runs to ask the user questions until they break by typing 'q'
while True:

    # code picks two random states from the state_info dictionary, assigns their name, ID code, and poverty rate to variables
    state1_name, state1_ID = random.choice(list(state_info.items()));state1_ID = state1_ID[0]
    state1_poverty = get_census_data_state(state1_ID)

    # code for random state 2 name, ID, and poverty assignment
    # while loop and state2_name assignment ensure code will run once, then again if both states end up being the same
    # preventing a duplicate assignment of both states
    state2_name = state1_name
    while state2_name == state1_name:
        state2_name, state2_ID = random.choice(list(state_info.items()));state2_ID = state2_ID[0]
        state2_poverty = get_census_data_state(state2_ID)

    # code below picks a random industry and assigns to random_industry, assigns state1_industry_num_employees
    # and state2_industry_num_employees with the amount of employees working in that industry for the states which
    # were randomly selected earlier

    # while loop runs again, selecting another random industry, if the number of employees is the same for both states.
    # this prevents a tie
    # state 1 and 2 industry_num_employees assignment ensures code will run at least once
    state1_industry_num_employees = 0
    state2_industry_num_employees = 0
    while state1_industry_num_employees == state2_industry_num_employees:
        state1_industry_list = get_industry_list_state(state1_ID);state2_industry_list = get_industry_list_state(state2_ID)
        random_industry, state1_industry_num_employees = random.choice(list(state1_industry_list.items()))
        state1_industry_num_employees = int(state1_industry_num_employees)
        # ERROR- random_industry key selected from state1_industry_list may not be present in state2_industry_list.
        # producing a key error when you try to obtain the amount of employees from that industry list
        # solved - solution: set state2_num_employees to 0 if the random_industry key is not in state2_industry_list.
        if random_industry in state2_industry_list:
            state2_industry_num_employees = state2_industry_list[random_industry]
            state2_industry_num_employees = int(state2_industry_num_employees)
        else:
            state2_industry_num_employees = 0

    # code determines which state is the correct answer, for the industry question
    if state1_industry_num_employees > state2_industry_num_employees:
        answer = 1
    elif state2_industry_num_employees > state1_industry_num_employees:
        answer = 2

    # code determines which state is the correct answer, for the poverty question
    if state1_poverty > state2_poverty:
        answer2 = 1
    elif state1_poverty == state2_poverty:
        # answer 3 is for a tie
        answer2 = 3
    else:
        answer2 = 2

    question_chance = random.randrange(11)
    # the question has an 10/11 chance of selecting a question comparing industries
    # else it will select a question comparing poverty levels between states
    if question_chance < 10:
        #todo insert while loop - use input to select which question will be asked - replace random question selection
        #delete question chance
        #add to main body of code excluding functions

        print(f'Which state has more employees working in {random_industry}?')
        print(f'press 1 for {state1_name}, press 2 for {state2_name}, press q to quit.')

        user_input = input()

        if user_input == 'q':
            break
        elif user_input == '1':
            choice = 1
        elif user_input == '2':
            choice = 2

        if answer == choice:
            # answer is correct, +1 point, there is the possibility of additional points,
            # if the values compared are similar enough
            print(f"Correct")
            print(f"{state1_name} has {state1_industry_num_employees} employees working in {random_industry}")
            print(f"{state2_name} has {state2_industry_num_employees} employees working in {random_industry}")
            points += 1

            print(f"+1 point.");print(f" Current points: {points}");print()

            if state2_industry_num_employees != 0:
                comparison_ratio = state1_industry_num_employees / state2_industry_num_employees
            else:
                comparison_ratio = 0
            # ratio compares how similar values are, if statement avoids divide by 0 error

            if 1.01 > comparison_ratio > 0.99:
                # bonus 2 points awarded if values are within 1% of each other
                points += 2
                print(f"Numbers of employees are within 1% of each other, 2 bonus points.")
                print(f" Current points: {points}")
            elif 1.1 > comparison_ratio > 0.9:
                # bonus point awarded if values are within 10% of each other
                points += 1
                print(f"Numbers of employees are within 10% of each other, Bonus point.")
                print(f" Current points: {points}")

        else:
            # answer is incorrect, 1 point is deducted, unless points is already 0
            print(f"Incorrect")
            print(f"{state1_name} has {state1_industry_num_employees} employees working in {random_industry}")
            print(f"{state2_name} has {state2_industry_num_employees} employees working in {random_industry}")
            points -= 1
            if points < 0:
                points = 0

            print(f"-1 point, unless you already have 0.");print(f" Current points: {points}");print()

    # the 1/11 chance that a poverty question will be asked.
    else:
        print(f'Which state has a higher rate of poverty?')
        print(f'press 1 for {state1_name}, press 2 for {state2_name}, press q to quit.')

        user_input = input()

        if user_input == 'q':
            break
        elif user_input == '1':
            choice2 = 1
        elif user_input == '2':
            choice2 = 2

        if answer2 == choice2:
            # answer is correct, +1 point, there is the possibility of additional points,
            # if the values compared are similar enough
            print(f"Correct")
            print(f"{state1_name}'s poverty rate is {state1_poverty}%")
            print(f"{state2_name}'s poverty rate is {state2_poverty}%")
            points += 1

            print(f"+1 point.");
            print(f" Current points: {points}");
            print()

            comparison_ratio = state1_poverty / state2_poverty
            # ratio compares how similar values are

            if 1.01 > comparison_ratio > 0.99:
                # bonus 2 points awarded if values are within 1% of each other
                points += 2
                print(f"Poverty levels are within 1% of each other, +2 bonus points.")
                print(f" Current points: {points}")
            elif 1.1 > comparison_ratio > 0.9:
                # bonus point awarded if values are within 10% of each other
                points += 1
                print(f"Poverty levels are within 10% of each other, Bonus point.")
                print(f" Current points: {points}")

        elif answer2 == 3:
            # tie
            print("Tie. Next question")
        else:
            # answer is incorrect, 1 point is deducted, unless points is already 0
            print(f"Incorrect")
            print(f"{state1_name}'s poverty rate is {state1_poverty}")
            print(f"{state2_name}'s poverty rate is {state2_poverty}")
            points -= 1
            if points < 0:
                points = 0

            print(f"-1 point, unless you already have 0.");print(f" Current points: {points}");print()





