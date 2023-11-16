from subprocess import PIPE, run, check_output, CalledProcessError, Popen, STDOUT, call
import unittest
import xmlrunner
import re

class TestMyProgram(unittest.TestCase):    
    
    def make(target):
        run(target, capture_output=True)
    def exist(self, fname):
        self.assertEqual(call( ['test', '-f', fname] ), 0, "the tested program doesn't exist")
        
    def launch(self, fname, input, supress_err=False):
        self.exist(fname)
        output = b''
        try:
            p = Popen(['./'+fname], stdin=PIPE, stdout=PIPE, stderr=STDOUT, shell=True)
            output = p.communicate(input.encode())[0]
            return (output.decode(), p.returncode)
        except CalledProcessError as exc:
            return (exc.output.decode(), exc.returncode)
            
    def test_simple(self):
        simple_keys = ['first word', 'second word', 'third word']
        simple_values = ['first word explanation', 'second word explanation', 'third word explanation']
        for i in range(len(simple_values)):
            str = self.launch('main', simple_keys[i] + '\n', False)[0]
            self.assertEqual(str, simple_values[i] + '\n', "")
            
    def test_too_long_key(self):
        too_long_key = 'a'*256 + '\n'
        str = self.launch('main', too_long_key, False)[0]
        self.assertEqual(str, "Your key is too large!\n", "")
        
    def test_negative(self):
        keys = ['sex', 'pistols', 'wetpussy']
        for i in range(len(keys)):
            str = self.launch('main', keys[i] + '\n', False)[0]
            self.assertEqual(str, "Our dictinary doesn't contain this key!\n", "")
    def test_too_long_but_legal(self):
        too_long_key = 'a'*255 + '\n'
        str = self.launch('main', too_long_key, False)[0]
        self.assertEqual(str, "Our dictinary doesn't contain this key!\n", "")


TestMyProgram.make('make')

if __name__ == "__main__":
    with open('report.xml', 'w') as report:
        unittest.main(testRunner=xmlrunner.XMLTestRunner(output=report), failfast=False, buffer=False, catchbreak=False)
