import json
import time
import random
import json
from locust import HttpUser, task, tag, between


# Статичные данные для тестирования


class RESTServerUser(HttpUser):
    """ Класс, эмулирующий пользователя / клиента сервера """
    wait_time = between(0, 0)       # время ожидания пользователя перед выполнением новой task

    # Адрес, к которому клиенты (предположительно) обращаются в первую очередь (это может быть индексная страница, страница авторизации и т.п.)
    #def on_start(self):
    #    self.client.get("/docs")    # базовый класс HttpUser имеет встроенный HTTP-клиент для выполнения запросов (self.client)

    @tag("get_all_condition")
    @task(80)
    def get_all_condition(self):
        """ Тест GET-запроса """
        user_id = random.randint(2, 7)      # генерируем случайный id в диапазоне [4, 6]     
        with self.client.get(f'/condition/profile/{user_id}',        # тест get-метода, получение данных о доступе в квартиру
                             catch_response=True,
                             name='/condition/profile/{user_id}') as response:
            # Если получаем код HTTP-код 200, то оцениваем запрос как "успешный"
            if response.status_code == 200:
                response.success()
            # Иначе обозначаем как "отказ"
            else:
                response.failure(f'Status code is {response.status_code}')

    @tag("get_one_condition")
    @task(50)
    def get_one_condition(self):
        """ Тест GET-запроса """
        condition_id = random.randint(24, 62)      # генерируем случайный id в диапазоне [4, 6]     
        with self.client.get(f'/condition/get/{condition_id}',        # тест get-метода, получение данных о доступе в квартиру
                             catch_response=True,
                             name='/condition/get/{condition_id}') as response:
            # Если получаем код HTTP-код 200, то оцениваем запрос как "успешный"
            if response.status_code == 200:
                response.success()
            # Иначе обозначаем как "отказ"
            else:
                response.failure(f'Status code is {response.status_code}')

    @tag("get_all_test")
    @task(4)
    def get_all_test(self):
        """ Тест GET-запроса (получение одной записи) """
        with self.client.get(f'/test/all',
                             catch_response=True,
                             name='/test/all') as response:
            # Если получаем код HTTP-код 200 или 204, то оцениваем запрос как "успешный"
            if response.status_code == 200 or response.status_code == 204:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')

    @tag("get_one_test")
    @task(3)
    def get_one_test(self):
        """ Тест GET-запроса (получение одной записи) """
        test_id = random.randint(1, 6) 
        with self.client.get(f'/test/{test_id}',
                             catch_response=True,
                             name='/test/{test_id}') as response:
            # Если получаем код HTTP-код 200 или 204, то оцениваем запрос как "успешный"
            if response.status_code == 200 or response.status_code == 204:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')

    
    @tag("authorization")
    @task(90)
    def authorization(self):
        """ Тест PosT-запроса (обновление записи о погоде) """
        test_data = {'username': "grifffff",
                     'password': "292211gum"
                     }
        post_data = json.dumps(test_data)
        # отправляем PUT-запрос на адрес <SERVER>/api/weatherforecast/{city_name}
        with self.client.post('/auth/jwt/create/',
                             catch_response=True,
                             name='/auth/jwt/create/',
                             data=post_data,
                             headers={'content-type': 'application/json'}) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')

    """
    @tag("register")
    @task(2)
    def register(self):
        registerRand_id = random.randint(1, 999999999)
        test_data = {'email': "eduarddaukaev"+str(registerRand_id)+"@gmail.com",
                     'id':6,
                     'password': "292211gum",
                     're_password': "292211gum",
                     'username': "Olya"+str(registerRand_id),
                     'age':20,
                     'first_name': "Olya",
                     'second_name': "TESTOlya",
                     'sex':"Женский"
                     }
        post_data = json.dumps(test_data)
        # отправляем PUT-запрос на адрес <SERVER>/api/weatherforecast/{city_name}
        with self.client.post('/auth/users/',
                             catch_response=True,
                             name='/auth/users/',
                             data=post_data,
                             headers={'content-type': 'application/json'}) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')
    """

    @tag("post_condition")
    @task(12)
    def post_condition(self):
        """ Тест PosT-запроса (обновление записи о погоде) """
        test_data = {"id": 2,
		"assessment": 5,
		"description": "4",
		"date": "2023-04-02T20:12:51.854565",
		"profile": 3,
		"work": 0,
		"reading": 0,
		"workout": 0,
		"gaming": 0,
		"family": 0,
		"friend": 0,
		"study": 0,
		"music": 0,
		"movie": 0,
		"shopping": 0,
		"travel": 0,
		"cleaning": 0,
		"sleep": 0,
		"party": 0,
		"bar": 0,
		"leisure": 0,
		"rendezvous": 0,
		"TV": 0
                     }
        post_data = json.dumps(test_data)
        # отправляем PUT-запрос на адрес <SERVER>/api/weatherforecast/{city_name}
        with self.client.post('/condition/create',
                             catch_response=True,
                             name='/condition/create',
                             data=post_data,
                             headers={'content-type': 'application/json'}) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')

    @tag("put_condition")
    @task(30)
    def put_condition(self):
        """ Тест PuT-запроса (создание записи о погоде) """
        # Генерируем случайные данные в опредленном диапазоне
        chislo = random.randint(24,60)
        test_data = {"id": chislo,
                     "assessment": 3,
		"description": "41",
		"date": "2023-04-02T20:12:51.854565",
		"profile": 3,
		"work": 0,
		"reading": 0,
		"workout": 0,
		"gaming": 0,
		"family": 0,
		"friend": 0,
		"study": 0,
		"music": 1,
		"movie": 0,
		"shopping": 0,
		"travel": 0,
		"cleaning": 0,
		"sleep": 0,
		"party": 0,
		"bar": 0,
		"leisure": 0,
		"rendezvous": 0,
		"TV": 0
                     }
        put_data = json.dumps(test_data)       # сериализуем тестовые данные в json-строку
        # отправляем POST-запрос с данными (POST_DATA) на адрес <SERVER>/api/weatherforecast
        with self.client.put('/condition/update',
                              catch_response=True,
                              name='/condition/update', data=put_data,
                              headers={'content-type': 'application/json'}) as response:
            # проверяем, корректность возвращаемого HTTP-кода
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')

  
    @tag("post_result_test")
    @task(12)
    def post_result_test(self):
        """ Тест PosT-запроса (обновление записи о погоде) """
        test_data = {	"id": 1,
		"profile": 3,
		"test": 2,
		"description": "haaaaaaaaaaaaaaaaa",
		"test_completion_time": 4,
		"score": 56,
	"date":"2023-03-31T17:36:15.486392"
                     }
        post_data = json.dumps(test_data)
        # отправляем PUT-запрос на адрес <SERVER>/api/weatherforecast/{city_name}
        with self.client.post('/resultTest/create',
                             catch_response=True,
                             name='/resultTest/create',
                             data=post_data,
                             headers={'content-type': 'application/json'}) as response:
            if response.status_code == 201:
                response.success()
            else:
                response.failure(f'Status code is {response.status_code}')


    
    @tag("get_analysis")
    @task(10)
    def get_all_condition(self):
              # генерируем случайный id в диапазоне [4, 6]     
        with self.client.get(f'/analysis/3/work',        # тест get-метода, получение данных о доступе в квартиру
                             catch_response=True,
                             name='/analysis/3/work') as response:
            # Если получаем код HTTP-код 200, то оцениваем запрос как "успешный"
            if response.status_code == 200:
                response.success()
            # Иначе обозначаем как "отказ"
            else:
                response.failure(f'Status code is {response.status_code}')
     