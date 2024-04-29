#include <iostream>
#include <limits.h>
#include <queue>
#include <unordered_map>
#include <list>

using namespace std;

struct Node {
	int type;
	char previous_step;
	bool was_visit = false;
	int short_way = INT_MAX;
	list<int> neighbors = {};
};
unordered_map<int, Node> nodes;
class Compare {
public:
	bool operator()(int a, int b) {
		if (nodes[a].short_way > nodes[b].short_way) {
			return true;
		}
		return false;
	}
};
priority_queue<int, vector<int>, Compare> front_line;
int main() {
	int x, y, begin_x, begin_y, end_x, end_y;
	cin >> y >> x >> begin_y >> begin_x >> end_y >> end_x;
	for (int i = 0; i < y; i++) {
		string current_string;
		cin >> current_string;
		for (int j = 0; j < x; j++) {
			if (current_string[j] == '.') {
				nodes[i * x + j + 1] = { .type = 1 };
			}
			if (current_string[j] == 'W') {
				nodes[i * x + j + 1] = { .type = 2 };
			}
		}
	}
	for (int i = 1; i <= x * y; i++) {
		if (nodes.contains(i)) {
			if (nodes.contains(i - 1) && (i - 1) % x != 0) {
				nodes[i].neighbors.push_back(i - 1);
			}
			if (nodes.contains(i + 1) && (i + 1) % x != 1) {
				nodes[i].neighbors.push_back(i + 1);
			}
			if (nodes.contains(i + x)) {
				nodes[i].neighbors.push_back(i + x);
			}
			if (nodes.contains(i - x)) {
				nodes[i].neighbors.push_back(i - x);
			}
		}
	}
	front_line.push(begin_x + (begin_y - 1) * x);
	nodes[begin_x + (begin_y - 1) * x].short_way = 0;
	nodes[begin_x + (begin_y - 1) * x].was_visit = true;
	while (!front_line.empty()) {
		int current_top_node = front_line.top();
		front_line.pop();
		for (int node_number : nodes[current_top_node].neighbors) {
			if (nodes[node_number].short_way > nodes[current_top_node].short_way + nodes[node_number].type) {
				nodes[node_number].short_way = nodes[current_top_node].short_way + nodes[node_number].type;
				
				if (node_number - current_top_node == x) {
					nodes[node_number].previous_step = 'S';
				}
				if (node_number - current_top_node == -x) {
					nodes[node_number].previous_step = 'N';
				}
				if (node_number - current_top_node == -1) {
					nodes[node_number].previous_step = 'W';
				}
				if (node_number - current_top_node == 1) {
					nodes[node_number].previous_step = 'E';
				}
			}
			if (nodes[node_number].was_visit == false) {
				front_line.push(node_number);
				nodes[node_number].was_visit = true;
			}
		}
	}
	int current_node = end_x + (end_y - 1) * x;
	int result_way = nodes[current_node].short_way;
	string result_str = "";
	if (result_way == INT_MAX) {
		cout << -1;
		return 0;
	}
	while (current_node != begin_x + (begin_y - 1) * x) {
		switch (nodes[current_node].previous_step) {
		case 'S':
			result_str = 'S' + result_str;
			current_node -= x;
			break;
		case 'N':
			result_str = 'N' + result_str;
			current_node += x;
			break;
		case 'W':
			result_str = 'W' + result_str;
			current_node += 1;
			break;
		case 'E':
			result_str = 'E' + result_str;
			current_node -= 1;
			break;
		}
	}
	cout << result_way << endl;
	cout << result_str;
	return 0;
}
