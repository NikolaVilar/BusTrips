# BusTrips – GTFS Java Application

This is a command-line Java application that returns upcoming bus arrivals for a selected stop. This app is using public transit schedule data in GTFS format.

The app was developed as part of demonstrating memory performance, input validation, and clean structure.

## Features

- Efficient processing of GTFS data without loading everything into memory
- Returns upcoming bus arrivals in either relative or absolute time format
- Supports filtering by number of upcoming buses per route
- Validates all input parameters and provides helpful feedback

## Requirements

- Java 17 or higher
- GTFS dataset (in text format) placed in a `data/` directory

## How to Compile and Run

### Compile

From the root of the project:

```bash
javac -encoding UTF-8 -d out src/bustrips/*.java src/bustrips/data/*.java src/bustrips/logic/*.java src/bustrips/model/*.java
```

### Run

Also from the root of the project:

```bash
java -cp out busTrips.Main <stop_id> <max_buses> <relative|absolute>
```

## Example
```bash
java -cp out bustrips.Main 2 5 relative
```

### Example output:
```
Postajalisce AL Masjid Al-nabawi (Clock Roundabout)
101: 10min, 18min, 25min, 30min, 38min
106: 12min, 20min, 32min, 45min
```

### Used GTFS Files

All files must be placed in a `data/` directory in the project root.
The application only requires the following files from the GTFS dataset:

- `stops.txt` defines stop names and IDs

- `stop_times.txt` contains arrival times for each stop

- `trips.txt` links trips to routes

- `routes.txt` defines the public-facing route numbers



## Input Validation

The program checks the following:

- That the stop ID exists in `stops.txt`
- That the bus count is a positive integer
- That the time format argument is either `relative` or `absolute`
- Invalid input triggers a helpful message and optionally lists valid stop IDs.

## Project Structure

## License

This project is intended for educational and demonstration purposes.
