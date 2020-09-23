import serial
import time
import pandas as pd
import atexit


ser = serial.Serial(
    port='COM3',
    baudrate=9600
)
df = pd.DataFrame(columns=['time', 'x', 'y'])
time_start = time.time()

def toExcel(df):
    print("exit!")
    df.to_excel('straight.xlsx')

i = 0
while True:
    if ser.readable():
        time_now = time.time()
        time_gap = time_now - time_start

        res = ser.readline()
        result = res.decode()[:len(res)-1]
        spl = result.split(' ')
        df = df.append({'time' : time_gap, 'x' : spl[0], 'y' : spl[1]}, ignore_index=True)
        print('add : ',result)
        print(df)

    i += 1
    if i == 20:
        toExcel(df)
        break
