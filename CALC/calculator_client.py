import socket
import struct

def showOptions():
	print ('Ensira sua expressão para ser calculada: ')	
	print ('Example: 2 + 3');

def convert_bytes_to_float(bnum):
	return float (struct.unpack('f', bnum)[0])

def sendRequest(opt, server_address):
	sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	try:
	    sent = sock.sendto(opt.encode('UTF-8'), server_address)
	    data, server = sock.recvfrom(4096)
	    
	    print (convert_bytes_to_float(data))
	finally:
	    sock.close()

udp_ip = 'localhost' # input('Insert IP adress: ')

udp_port = 9999 # int(input('Insert PORT: '))

showOptions()

opt = input('Insert option: ')

sendRequest(opt, (udp_ip, udp_port))



