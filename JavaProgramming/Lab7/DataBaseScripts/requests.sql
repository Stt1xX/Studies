select m.*, o.* from movies as m
join movies_operators on m.id = movies_operators.id_movie
join operators as o on movies_operators.id_Operator = o.operatorId
;