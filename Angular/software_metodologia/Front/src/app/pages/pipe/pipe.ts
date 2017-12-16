import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'FilterPipe',
})
export class FilterPipe implements PipeTransform {
    
    transform(value: any, input: any, ) {
        
        if (input) {
            input = input.toLowerCase();
            return value.filter(function (el: any) {
                return el.email.toLowerCase().indexOf(input) > -1;
            })
        }
        return value;
    }
}