#include <iostream>
#include <list>
#include <unordered_map>
#include <queue>

using namespace std;

struct Node {
	int color = -1;
	list<int> neighbors = {};
	bool was_visited = false;
};

unordered_map<int, Node> main_map;
queue<int> front_line;

int main() {
	int n, m;
	cin >> n >> m;
	for (int i = 0; i < m; i++) {
		int first, second;
		cin >> first >> second;
		main_map[first].neighbors.push_back(second);
		main_map[second].neighbors.push_back(first);
	}
	for (auto [key, value] : main_map) {
		if (value.was_visited == true) {
			continue;
		}
		front_line.push(key);
		main_map[key].was_visited = true;
		main_map[key].color = 0;
		while (!front_line.empty()) {
			int current_top = front_line.front();
			front_line.pop();
			for (int x : main_map[current_top].neighbors) {
				if (main_map[x].was_visited == false) {
					main_map[x].color = (main_map[current_top].color + 1) % 2;
					main_map[x].was_visited = true;
					front_line.push(x);
				}
			}
		}
	}
	for (auto [key, value] : main_map) {
		for (int x : value.neighbors) {
			if (value.color == main_map[x].color) {
				cout << "NO";
				return 0;
			}
		}
	}
	cout << "YES";
	return 0;
}