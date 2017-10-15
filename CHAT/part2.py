import socket

def main():
  socket_tcp = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
  socket_tcp.connect(("localhost",8000));
  
  while True:
    mensagem = raw_input()
    socket_tcp.send(mensagem)
    msg = socket_tcp.recv(2048)
    print ("Participante 1: "+msg)

main()
