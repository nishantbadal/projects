.globl main
.data
    arrA: .space 24
    arrB: .space 24
    arrC: .space 36
    arrD: .space 36

    fileA: .asciiz "/Users/nishantbadal/Dropbox/Masters/architecture/A.in"
    fileB: .asciiz "/Users/nishantbadal/Dropbox/Masters/architecture/B.in"
    fileC: .asciiz "/Users/nishantbadal/Dropbox/Masters/architecture/C.in"
    fileD: .asciiz "/Users/nishantbadal/Dropbox/Masters/architecture/D.out"

    numberSep: .asciiz "   "
    
.text
main:
#***************************************
#open matrixA
    #open file
    li $v0, 13 
    #load file name       
    la $a0, fileA
    #file flag        
    li $a1, 0
    #file mode        
    li $a2, 0       
    syscall  
    #store fd in $s0        
    add $s0, $v0, $0        

#read 24 bytes from matrixA and store in array
    #read from file
    li $v0, 14   
    #$s0 contains fd         
    add $a0, $s0, $0 
    #allocate space for bytes loaded       
    la $a1, arrA 
    #number of bytes to read       
    li $a2, 24            
    syscall
    
#close file
    li $v0, 16  
    syscall

#***************************************    
#open matrixB
    li $v0, 13            
    la $a0, fileB        
    li $a1, 0        
    li $a2, 0        
    syscall                
    add $s0, $v0, $0        

#read matrixB
    li $v0, 14            
    add $a0, $s0, $0        
    la $a1, arrB        
    li $a2, 24            
    syscall
    
#close matrixB
    li $v0, 16  
    syscall
    
#***************************************
#open matrixC
    li $v0, 13            
    la $a0, fileC        
    li $a1, 0       
    li $a2, 0        
    syscall                
    add $s0, $v0, $0        

#read matrixC
    li $v0, 14            
    add $a0, $s0, $0        
    la $a1, arrC        
    li $a2, 36            
    syscall

#close matrixC
    li $v0, 16  
    syscall
    
#***************************************
#multiply matrices stored in arrA * arrB
    li $t0, 0 #index of first num in row for matrix A
    li $t1, 4 #index of second num in row for matrix A
    li $t2, 0 #index of first num in row for matrix B
    li $t3, 4 #index of second num in row for matrix B
    li $t4, 0 #index of matrix D
    li $t5, 0 #index used to add matrix C
    whileA:
        #once all values in A are done, branch to addMatrix
        beq $t0, 24, addMatrices
        #grab row of matrix A
        lwc1 $f2, arrA($t0)
        lwc1 $f4, arrA($t1)
        
        
        #jump to columns in matrix B
        j whileB
        
    whileBComplete:
        #second half of matrix A while loop
        #reset matrixB indexes
        li $t2, 0 #index of first num in row for matrix B
        li $t3, 4 #index of second num in row for matrix B
        #increment arrA indexes
        addi $t0, $t0, 8
        addi $t1, $t1, 8
        
        #loop
        j whileA
        
   whileB:
       #branch back to whileA if all nums in matrix B are read
       beq $t2, 24, whileBComplete
       #grab column
       lwc1 $f6, arrB($t2)
       lwc1 $f8, arrB($t3)
       
       #increment arrB indexes
       addi $t2, $t2, 8
       addi $t3, $t3, 8
       #matrix multiplication
       mul.s $f10, $f2, $f6
       mul.s $f14, $f4, $f8
       add.s $f16, $f10, $f14
       
       #store in arrD
       swc1 $f16, arrD($t4)
       
       #increment arrD index
       addi $t4, $t4, 4
       #loop
       j whileB
        
        
addMatrices:
    #branch when $t5 equals 36
    beq $t5, 36, writeToD
    #load vals
    lwc1 $f0, arrD($t5)
    lwc1 $f1, arrC($t5)
    add.s $f2, $f0, $f1
    swc1 $f2, arrD($t5)
    
    #print num
        lwc1 $f12, arrD($t5)
        li $v0, 2
        syscall
        
        li $v0, 4
        la $a0, numberSep
        syscall
        
    add $t5, $t5, 4            # Increment to the next float
    #loop
    j addMatrices
    

writeToD:
#open fileD
    li $v0, 13            
    la $a0, fileD        
    li $a1, 1        
    li $a2, 0        
    syscall                
        
#write to fileD
    move $a0, $v0
    li $v0, 15                    
    la $a1, arrD    
    li $a2, 36            
    syscall
    
#closeFileD
    li $v0, 16  
    syscall
    
# Exit Gracefully
    li	$v0, 10
    syscall
