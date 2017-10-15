import socket

def main():
  tcp = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
  tcp.bind(("localhost",8000))
  tcp.listen(1)
  con,addr = tcp.accept()

  while True:
    msg = con.recv(2048)
    print ("Participante 2: "+msg)
    mensagem = raw_input()
    con.send(mensagem)

main()
