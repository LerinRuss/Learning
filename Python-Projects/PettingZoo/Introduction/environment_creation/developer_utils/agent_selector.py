from pettingzoo.utils import agent_selector
agents = ["agent_1", "agent_2", "agent_3"]
selector = agent_selector(agents)
agent_selection = selector.reset()
# agent_selection will be "agent_1"
for i in range(100):
    agent_selection = selector.next()
    # will select "agent_2", "agent_3", "agent_1", "agent_2", "agent_3", ..."
    print(agent_selection)
