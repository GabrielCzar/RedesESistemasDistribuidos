import socket

def showOptions():
	print ('\\UPTIME - Server execution time')	
	print ('\\REQNUM - Quantity of the server')	
	print ('\\CLOSE - Client stop')

def sendRequest(opt, server_address):
	sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
	try:
	    sent = sock.sendto(opt.encode('UTF-8'), server_address)
	    data, server = sock.recvfrom(4096)
	    
	    if opt == '\\REQNUM':
		    print(int.from_bytes(data, byteorder='big'))
	    else:
		    print (data.decode('UTF-8'))
	finally:
	    sock.close()

udp_ip = input('Insert IP adress: ')

udp_port = int(input('Insert PORT: '))

showOptions()

opt = input('Insert option: ')

if opt == '\\CLOSE':
    exit(1)

sendRequest(opt, (udp_ip, udp_port))



