Feature: firstTest

  Scenario Outline: Vailidate the Drivers repose against the schema
    Given the URI '<uri>' is called and the endpoint is '<endpoint>' and the status code is '<statusCode>' then the response matches the schema
    Examples:
      | uri                      | endpoint      | statusCode | responseBody                  | expectedFields |
      | http://ergast.com/api/f1 | /drivers.json | 200        | MRData.DriverTable.Drivers[0] | driverId       |

  Scenario Outline: Validate Drivers data via examples table
    Given the URI '<uri>' is called and the endpoint is '<endpoint>' and the status code is '<statusCode>' then the responsePath '<responsePath>' and field '<field>' contains '<expectedValue>'
    Examples:
      | uri                      | endpoint              | statusCode | responsePath                   | field       | expectedValue                                  |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | driverId    | abate                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | url         | http://en.wikipedia.org/wiki/Carlo_Mario_Abate |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | givenName   | Carlo                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | familyName  | Abate                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | dateOfBirth | 1932-07-10                                     |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | nationality | Italian                                        |

  Scenario: Validate Drivers data via data table with List
    Given the endpoint is called successfully then the responsePathFields should contain the expected values
      | uri                      | endpoint              | statusCode | responsePath                   | field    | expectedValue |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | driverId | abate         |

  Scenario: Validate Drivers data via data table with asMaps
    Given the endpoint is called successfully then the responsePathFields should contain the expected values with asMaps
      | uri                      | endpoint              | statusCode | responsePath                   | field       | expectedValue                                  |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | driverId    | abate                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | url         | http://en.wikipedia.org/wiki/Carlo_Mario_Abate |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | givenName   | Carlo                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | familyName  | Abate                                          |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | dateOfBirth | 1932-07-10                                     |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers[0]. | nationality | Italian                                        |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.                        | series      | f1                                             |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.                        | limit       | 1                                              |
      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.                        | total       | 857                                            |
#      | http://ergast.com/api/f1 | /drivers.json?limit=1 | 200        | MRData.DriverTable.Drivers     | driverId    | notNull                                        |

  Scenario: Validate Drivers data via data table - split steps
    Given the user intends to search
    When a specific driver 'abate' is searched
    Then the status code is '200'
    And the response contains the correct driver details
      | field       | expectedValue                                  | responsePath                   |
      | driverId    | abate                                          | MRData.DriverTable.Drivers[0]. |
      | url         | http://en.wikipedia.org/wiki/Carlo_Mario_Abate | MRData.DriverTable.Drivers[0]. |
      | givenName   | Carlo                                          | MRData.DriverTable.Drivers[0]. |
      | familyName  | Abate                                          | MRData.DriverTable.Drivers[0]. |
      | dateOfBirth | 1932-07-10                                     | MRData.DriverTable.Drivers[0]. |
      | nationality | Italian                                        | MRData.DriverTable.Drivers[0]. |