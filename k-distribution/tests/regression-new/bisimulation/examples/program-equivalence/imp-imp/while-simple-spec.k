module SYNC-POINT-CANDIDATES

imports MAIN

claim
<s> ~ a => ^ b ... </s>
<ak> while (_) _ </ak>
[trusted]

claim
<s> ~ a => ^ b ... </s>
<ak> .K </ak>
[trusted]

claim
<s> ~ b => ^ a ... </s>
<bk> while (_) _ </bk>
[trusted]

claim
<s> ~ b => ^ a ... </s>
<bk> .K </bk>
[trusted]

endmodule

module WHILE-SIMPLE-SPEC

imports MAIN
imports SYNC-POINT-CANDIDATES

syntax Id ::= "n" [token]

// simple loop
claim
<k> check => . </k>
<s> ^ a ... </s>
<aT>
  <ak>
    while (n <= 10) {
      n = n + 1;
    }
  =>
    ?_
  </ak>
  <astate> n |-> N1:Int _ => ?_ </astate>
</aT>
<bT>
  <bk>
    while (n <= 10) {
      n = n + 1;
    }
  =>
    ?_
  </bk>
  <bstate> n |-> N2:Int _ => ?_ </bstate>
</bT>
requires N1 ==Int N2

claim
<k> check => . </k>
<s> ^ a ... </s>
<aT>
  <ak> .K </ak>
  <astate> n |-> N1:Int _ </astate>
</aT>
<bT>
  <bk> .K </bk>
  <bstate> n |-> N2:Int _ </bstate>
</bT>
requires N1 ==Int N2
[trusted]

endmodule
