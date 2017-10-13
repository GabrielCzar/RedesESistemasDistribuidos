import socket
import struct

OPERATIONS = {
	'+' : lambda x, y: x + y,
	'-' : lambda x, y: x - y,
	'*' : lambda x, y: x * y,
	'/' : lambda x, y: x / y
}

def calculate(data):
	global OPERATIONS
	x, y, opt = data
	return OPERATIONS[opt](x, y)

def transform(expression): 	
	expression = expression.decode('UTF-8')
	x, opt, y = expression.split(' ')
	x = float(x)
	y = float(y)
	return x, y, opt
	
def convert_float_to_bytes(num): 
	return struct.pack('f', num)

UDP_IP = '127.0.0.1'
UDP_PORT = 9999 #int(input('Insert server port: '))

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

sock.bind((UDP_IP, UDP_PORT))

print('Waiting connections')

while True:
	expression, addr = sock.recvfrom(4096)
	
	answer = None

	try:
		answer = convert_float_to_bytes(
					calculate(
						transform(expression)))
	except Exception as e:
		answer = b'Error'
		print(e)

	sock.sendto(answer, addr)
	

	

