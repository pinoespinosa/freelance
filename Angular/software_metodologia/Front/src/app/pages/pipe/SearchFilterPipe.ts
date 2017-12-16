import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
 name: 'searchfilter'
})

@Injectable()
export class FilterPipe implements PipeTransform {
 transform(items: any[], field: string, value: string): any[] {

 	console.log("-----------------")
 	console.log(items)
 	console.log(field)
 	console.log(value)
 	console.log("-----------------")

   if (!items) return [];
   return items.filter(it => it[field] == value);
 }
}