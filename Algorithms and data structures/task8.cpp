#include <iostream>
#include <vector>


using namespace std;

int main() {
	int n, k;
	cin >> n >> k;
	vector<int> arr;
	int ret_sum = 0;
	for (int j = 0; j < n; j++) {
		int input;
		cin >> input;
		bool flag = false;
		for (int i = 0; i < size(arr); i++) {
			if (input >= arr[i]) {
				auto iter = arr.begin();
				arr.insert(iter + i, input);
				flag = true;
				break;
			}
		}
		if (!flag)
			arr.push_back(input);
		ret_sum += input;
	}

	for (int i = 1; i <= n; i++) {
		if (i % k == 0) {
			ret_sum -= arr[i - 1];
		}
	}
	cout << ret_sum;
	return 0;
}