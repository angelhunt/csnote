# attributeds

## read by compiler
 classes or methods;  assembly or module.

  add semantic meaning to those elements. The compiler uses those semantic meanings to alter its output and report possible mistakes by developers using your code.
  *  [Conditional("TRACE_ON"/"DEBUG")]
  *  The Obsolete attribute marks a code element as no longer recommended for use


Nullable static analysis
* helps the compiler perform static analysis and determine when a variable is not null
* preconditions: AllowNull and DisallowNull, it should limit input parameters.. The AllowNull attribute specifies pre-conditions, and only applies to inputs(setter)., it does not affect the get accessor. 
* post-conditions: MaybeNull and NotNull， also work for ref/out 
* conditional post-conditions: NotNullWhen, MaybeNullWhen, and NotNullIfNotNull