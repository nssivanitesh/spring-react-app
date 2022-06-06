# Spring React App - Using TrafikLab API

## Requirements

### Functional Requirements
* Use API from (https://www.trafiklab.se/api/sl-hallplatser-och-linjer-2.)
* Find out the lines with most number of bus stops
* Present top 10 routes in a nice formatted way
* The webpage should show all the bus stops for the top 10 bus lines - no requirements on how bus stops sorted
  * Should all the bus stops be displayed at once?
  * Or, should the related bus stops be displayed - when a line is selected? `(Selected approach)`
* Data must come from the API

### Technical Requirements
* Use JavaScript for front-end
* Java for back-end

## Implementation

### Overview
Implement the front end logic with React JS, which communicates with a Spring boot backend server. In return the spring boot server, contacts the remote API provider, for the requested data.

#### Plugins used in the backend
* OpenFeign - For networking
* Jackson Core - For JSON to POJO deserialization
* Lombok - For generation of boilerplate code
* Hamcrest - Used during testing

#### Plugins used in the fontend
* TailwindCSS - For the UI
* AMCharts - For the word cloud visualization

## Environment setup
1. Clone https://github.com/nssivanitesh/spring-react-app

2. Folder `demo` contains the backend code
  1. Verify if you have Java configured
  2. Download all maven dependencies
  3. `demo\src\main\resources\application.properties` file contains the settings for:
    1. Base API URL - `sbab.app.baseURL`
    2. API Key - `trafiklab.api.key`
  4. The application will launch in the default port, let's call it `BACKEND_URL`
  
3. Folder `demoui` contains the front end code
  1. Verify if you have NodeJS and NPM installed on your machine
  2. Provide the `BACKEND_URL` in the environment variable (.env) file.
  3. Run `npm install` and `npm start` to start the application
