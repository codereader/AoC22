import os
import sys


def run_day(day):
    os.chdir(day)
    print(f"Running {day}")
    exec(open('main.py').read())


def run():
    day = input("Specify day to run: ")
    run_day(f"Day{int(day):02d}")


def run_latest():
    most_recent_day = sorted(filter(lambda s: s.startswith("Day"), os.listdir()), reverse=True)[0]
    run_day(most_recent_day)


if sys.argv[1] == "run":
    run()
elif sys.argv[1] == "run_latest":
    run_latest()