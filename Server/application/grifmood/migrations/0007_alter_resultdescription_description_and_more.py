# Generated by Django 4.1.2 on 2023-04-20 00:41

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grifmood', '0006_alter_condition_description'),
    ]

    operations = [
        migrations.AlterField(
            model_name='resultdescription',
            name='description',
            field=models.CharField(max_length=5000),
        ),
        migrations.AlterField(
            model_name='resulttest',
            name='description',
            field=models.CharField(max_length=5000),
        ),
        migrations.AlterField(
            model_name='test',
            name='description',
            field=models.CharField(max_length=5000),
        ),
    ]