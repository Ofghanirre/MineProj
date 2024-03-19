# Plan réalisation:

---

point w point : dualpoint

point w point x infinite : ligne

---

dualpoint w point : circle

ligne w point : plan

---

circle w point : sphere 

si sphere = 0 => point appartient à cercle

plan w point : espace

---

sphere w point : ?

si ? = 0 => point appartient à une sphere

---


Point : 
e1, e2, e3, ei

dualPoint:
e12 e31 e23, ei


Point /\ dualPoint:

e123 

-------

Current Result:

#### Contexte : 3 pts:
PA:
```
Type: POINT
 e1 : 122.21672829317393
 e2 : 86.0
 e3 : 175.71107811389453   
```
PB:
```
Type: POINT
 e1 : 123.54733073663407
 e2 : 86.0
 e3 : 177.50746063517042
```
PC:
```
Type: POINT
 e1 : 120.98465780525918
 e2 : 86.0
 e3 : 177.9799639788878 
```

#### OuterProduct (OP)
  
PA OP PB = DualP
```
 Type: DUAL_POINT
 e12 : -114.43181013757021
 e23 : 154.4888968297255
 e31 : 14.253595367983507
```

DualP OP PC = Circle
```
 Type: CIRCLE
 e123 : -449.9739270207683
 e01 : 10749.190737193047
 e02 : 22091.035804413787
 e03 : -28531.921989487364  
```

