Feature: A

  Scenario: Create Account OK
    Given an Account with Customer ID = 1
    When I call the Create Account endpoint
    Then the account should be created successfully

  Scenario: Create Account KO Wrong Customer Id
    Given an Account with Customer ID = -1
    When I call the Create Account endpoint
    Then I'll get HTTP Status Code 404 with message 'There is no Customer with ID = -1'

  Scenario: Create Account KO No Customer Id
    Given an Account with no Customer Id
    When I call the Create Account endpoint
    Then I'll get HTTP Status Code 400 with message 'You have to define a Customer ID for this account'

  Scenario: Get Account By Id OK
    Given an instance of the DB where there are multiple Accounts
    When I call the Get Account endpoint
    Then I'll get the corrispondent Account

  Scenario: Get Account By Id OK
    Given an instance of the DB where there are multiple Accounts
    When I call the Get Account endpoint with a wrong ID
    Then I'll get HTTP Status Code 404 with message 'There is no account with id = -1'






