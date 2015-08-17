** How to run tests -

Make sure you have chromedriver executable downloaded. Please path of that
chromedriver in my code. Currently it is /home/shikha/workspace/HomeworkAssignment/chromedriver.

Otherwise, you just run it is as any other javatest (junit4).

**What does the tests do:
It first loads Walmart.com. It nexts searches for socks and adds the selects (clicks) first search
result. It nexts adds that selected item to cart. It next clicks on checkout button, and 
logs in using an existing account. It finally checks that only the selected item is being 
checked out. 

Throughout all the blocking actions like waiting fro page load are done using a timeout. This
 means test will finish in definite time. If there is a failure at any step, the test stops right 
 there and test environment is cleaned up. 

**Readibility
All methods have inuitive readable names, thus documenting themselves. None of my methods are too long.

**Reusability
I have defined methods for common tasks like searching/selecting, waiting for page/JS to load,
adding an item to cart, waiting for checkout page to load etc. Besides, init/de-init code
that could be common to all tests is in @BeforeClass, @Before, and @After methods. 

This make it easy to add a new test like testCheckoutMultipleItems, testCheckoutManyQuanitiesOfOneItem, or testAddItemToRegistry.

**Extraction of generic methods, no code duplication
There isn't any code duplication as much as I can see. I have tried to extract out
generic methods as much as I can, but I have tried not to overdesign since coming
up with generic methods without having concrete usecase is hard.
