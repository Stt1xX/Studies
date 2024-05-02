#include <iostream>
#include <unordered_set>

using namespace std;

int main_arr[1010][1010] = {};
int current_main_arr[1010][1010] = {};
unordered_set<int> passed = {};

int n;

static void dfs(int from) {
	passed.insert(from);
	for (int i = 1; i <= n; i++) {
		if (!passed.contains(i) && current_main_arr[from][i] != -1) {
			dfs(i);
		}
	}
}

static void reverse_dfs(int from) {
	passed.insert(from);
	for (int i = 1; i <= n; i++) {
		if (current_main_arr[i][from] != -1 && !passed.contains(i)) {
			reverse_dfs(i);
		}
	}
}

int main() {

	cin >> n;

	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <= n; j++) {
			cin >> main_arr[i][j];
		}
	}

	int left = 0;
	int right = 1000000000;

	while (left != right) {
		int middle = (left + right) / 2;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (main_arr[i][j] <= middle) {
					current_main_arr[i][j] = main_arr[i][j];
				}
				else {
					current_main_arr[i][j] = -1;
				}
			}
		}
		passed.clear();
		dfs(1);
		if (passed.size() != n) {
			left = middle + 1;
			continue;
		}
		passed.clear();
		reverse_dfs(1);
		if (passed.size() != n) {
			left = middle + 1;
			continue;
		}
		right = middle;
	}
	cout << right;

	return 0;
}