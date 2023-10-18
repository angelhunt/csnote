heapq — Heap queue algorithm

直接对一个List调用，可以实现堆的pop, push
                heapq.heappop(self.max_heap)
                heapq.heappush(self.max_heap, (-diff, node.val))

python list本身也支持一些api，实现简单的queue, stack

pop(0)  删除头部第一个元素