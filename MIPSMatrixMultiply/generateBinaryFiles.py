import struct
data = struct.pack('6f', 1.0, 2.0, 3.0, 4.0, 6.0, 5.0)
with open('B.in', 'wb') as f:
    f.write(data)