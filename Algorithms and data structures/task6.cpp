#include <iostream>
#include <vector>
#include <string>

using namespace std;

int main() {
	
	string input;
	vector<string> arr;
	for (int i = 0; cin >> input; i++)
		arr.push_back(input);

	vector<string> sorted_arr;
	int index = 0;
	for (int i = 0; i < size(arr); i++) {
		string max_str = "!";
		for (int j = 0; j < size(arr); j++) {
			if ((max_str + arr[j]).compare(arr[j] + max_str) < 0) {
				max_str = arr[j];
				index = j;
			}
		}
		arr[index] = "!";
		//cout << max_str << " -  max" << endl;
		sorted_arr.push_back(max_str);
	}


	for (string str : sorted_arr) {
		cout << str;
	}
	return 0;
}