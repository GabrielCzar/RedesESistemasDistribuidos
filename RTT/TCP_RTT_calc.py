import subprocess, math

def read_data():
	min = 10
	qtd = str(min + int(input('Digite sua matricula: ')[-1]))
	url = input('Digite a url: ')
	comando = 'ping -c ' + qtd + ' ' + url

	saida_sub = subprocess.getoutput(comando)

	print (saida_sub)
	print ()

	rtts = saida_sub.split(' ')

	rtts_values = []
	l = []

	for v in rtts:
		if 'time=' in v:
			l.append(v.split('time=')[1])

	result = []
	for v in l:
		if len(v) > 0:
			result.append(float(v))

	return result


def calcular(data):
	num_rtt = len(data)
	rttinit = data[0]

	media = rttinit
	desvio = 0.0
	if num_rtt == 1:
		print("Media inicial: " + str(rttinit) + " ") 
		print("Desvio inicial: " + str(0.0) + "\n")
		return


	for i in range (1, len(data)):
		rttatual = data[i]

		media  = 0.875 * media + 0.125 * rttatual
		desvio = 0.75 * desvio + 0.25 *  abs(rttatual - media)
		print (str(i) + " Media = " + str(0.875) + " * media" + " + " + "0.125 "  + " * " + str(rttatual) + " = " + str(media) + " ")
		print (str(i) + " Desvio = " + "0.75" + " * " + "desvio" + " + " + " 0.25" + " * |media -  rtt| = "   + str(desvio) + "\n")

	print ("Timeout total: " + str(rttatual + 4 * desvio))

calcular(read_data())
