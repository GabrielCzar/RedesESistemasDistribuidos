import socket
from datetime import datetime

TIME = datetime.now()
REQNUM = 0

def getAnswer(opt):
	global TIME, REQNUM
	if opt == b'\\UPTIME':
		new_time = datetime.now() - TIME
		return str(new_time).encode('UTF-8')
	elif opt == b'\\REQNUM':
		return (REQNUM).to_bytes(10, byteorder='big')
	else:
		return b'Request Invalid'

UDP_IP = '127.0.0.1'
UDP_PORT = int(input('Insert server port: '))

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

sock.bind((UDP_IP, UDP_PORT))

print('Waiting connections')

while True:
	opt, addr = sock.recvfrom(4096)
	
	REQNUM += 1
	
	answer = getAnswer(opt)

	sock.sendto(answer, addr)

	

