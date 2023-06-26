

Table films {
  id integer [primary key]
  name varchar(300)
  description varchar(200)
  release_date timestamp
  duration integer
}

Table film_likes {
  film_id integer [primary key]
  liker_id integer
}

Table film_genres {
  film_id integer [primary key]
  genre_id integer [primary key]
}

Table genres {
  genre_id integer [primary key]
  name varchar(300)
}

Table film_ratings {
  film_id integer [primary key]
  rating_id integer
}

Table ratings {
  rating_id integer [primary key]
  name varchar(300)
}

Ref: film_likes.film_id > films.id

Ref: film_genres.film_id > films.id

Ref: film_genres.genre_id - genres.genre_id

Ref: films.id - film_ratings.film_id

Ref: film_ratings.rating_id - ratings.rating_id

Ref: film_likes.liker_id - users.id

Table users {
  id integer [primary key]
  email varchar(300)
  login varchar(300)
  name varchar(300)
  birth_day timestamp
}

Table user_friends {
  user_id integer [primary key]
  user_friend_id integer [primary key]
  added_to_friend_list boolean
  
}

Ref: users.id < user_friends.user_id
