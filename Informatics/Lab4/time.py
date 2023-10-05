import timeit

print('Время действия первой программы ------> ', timeit.timeit('import main; main.main()', number=100))

print('Время действия второй программы ------> ', timeit.timeit('import mainLib; mainLib.mainLib()', number=100))

print('Время действия третей программы ------> ', timeit.timeit('import mainRegular; mainRegular.mainRegular', number=100))