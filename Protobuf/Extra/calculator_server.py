import socket
import calculator_pb2

def main():
    s = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
    s.bind(("localhost", 9999))

    while True:
        print("Waiting requests...")
        opt = calculator_pb2.Request()
        answer = calculator_pb2.Reply()
        data, addr = s.recvfrom(1024) 

        opt.ParseFromString(data)
        answer.res = calc(opt.n1, opt.n2, opt.op)
        s.sendto(answer.SerializeToString(), addr)

        print ("Reply send to ", addr)

def calc(num1, num2, op):
        if op == calculator_pb2.Request.SOM:
            return num1 + num2
        elif op == calculator_pb2.Request.SUB :
            return num1 - num2
        elif op == calculator_pb2.Request.MUL:
            return num1 * num2
        elif op == calculator_pb2.Request.DIV:
            return num1 / num2


main()
