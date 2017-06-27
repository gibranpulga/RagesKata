

Ejercicio 2 
================================

Como input tienes un fichero en el que cada línea especifica un rango de números consecutivos definido por el número inicial y final [a b]  donde a < b . Por ejemplo [2 ,  5] especifica los números 2,3,4,5 .
El ejercicio es calcular la cantidad de números distintos que estan comprendidos en la unión de todos los rangos definidos en el fichero, teniendo en cuenta que los rangos pueden estar solapados o incluidos en otros rangos. Los números pueden estar comprendidos entre 0 y 2^31.

Por ejemplo, dados estos rangos : 
[1 4] 
[6 10]
[13 , 15]
[2 ,4]

La respuesta sería 12, ya que hay 12 números comprendidos entre todos los rangos:  1 , 2 , 3, 4 , 6 , 7, 8 , 9 , 10 , 13, 14, 15 

Devolver el resultado tanto para el fichero 'range_small.txt' como para 'range_large.txt'.
