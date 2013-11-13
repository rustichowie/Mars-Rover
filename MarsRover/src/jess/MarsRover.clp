(deftemplate gridbox
    (slot direction)
    (slot obstacle)
    (slot rocks (type INTEGER))
    (slot grain (type INTEGER))
    (slot came_from)
    (slot signal)
    (slot is_empty)
    
)

(deftemplate rover
    (slot name)
    (slot carrying)
)



(defrule rollout
?left <- (gridbox (direction left) (obstacle ?l_obs) (rocks ?l_rocks) (grain ?l_grain) (came_from ?l_came_from))
?right <- (gridbox (direction right) (obstacle ?r_obs) (rocks ?r_rocks) (grain ?r_grain) (came_from ?r_came_from))
?top <- (gridbox (direction top) (obstacle ?t_obs) (rocks ?t_rocks) (grain ?t_grain) (came_from ?t_came_from))
?bottom <- (gridbox (direction bottom) (obstacle ?b_obs) (rocks ?b_rocks) (grain ?b_grain) (came_from ?b_came_from))
=>


)
