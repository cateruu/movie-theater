import React from 'react';
import List from './List/List';
import { Movie } from '@/types/movies';
import { Paginated } from '@/types/pagination';

interface Props {
  selectedMovie: Movie;
  movies: Paginated<Movie>;
}

const MoviesList = async ({ selectedMovie, movies }: Props) => {
  return (
    <div className='w-full'>
      {movies.content.length > 0 ? (
        <List movies={movies.content} selectedMovie={selectedMovie} />
      ) : (
        <section>No movies :(</section>
      )}
    </div>
  );
};

export default MoviesList;
