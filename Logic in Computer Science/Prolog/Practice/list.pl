member(X, [X|_]).
member(X, [_|T]) :- member(X, T).

in_intersection(X, S1, S2) :- member(X, S1), member(X, S2).

in_product([X, Y], S1, S2):-
    member(X, S1),
    member(Y, S2).

in_diff(X, S1, S2) :-
    member(X, S1),
    \+ member(X, S2).